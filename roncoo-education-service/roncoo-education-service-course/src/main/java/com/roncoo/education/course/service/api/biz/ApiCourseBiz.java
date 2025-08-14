package com.roncoo.education.course.service.api.biz;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.roncoo.education.common.core.base.Page;
import com.roncoo.education.common.core.base.PageUtil;
import com.roncoo.education.common.core.base.Result;
import com.roncoo.education.common.core.enums.PutawayEnum;
import com.roncoo.education.common.core.enums.StatusIdEnum;
import com.roncoo.education.common.tools.BeanUtil;
import com.roncoo.education.common.service.BaseBiz;
import com.roncoo.education.course.dao.CategoryDao;
import com.roncoo.education.course.dao.CourseChapterDao;
import com.roncoo.education.course.dao.CourseChapterPeriodDao;
import com.roncoo.education.course.dao.CourseDao;
import com.roncoo.education.course.dao.impl.mapper.entity.*;
import com.roncoo.education.course.service.api.req.ApiCoursePageReq;
import com.roncoo.education.course.service.api.resp.ApiCategoryResp;
import com.roncoo.education.course.service.api.resp.ApiCoursePageResp;
import com.roncoo.education.course.service.biz.req.CourseReq;
import com.roncoo.education.course.service.biz.resp.CourseChapterPeriodResp;
import com.roncoo.education.course.service.biz.resp.CourseChapterResp;
import com.roncoo.education.course.service.biz.resp.CourseLecturerResp;
import com.roncoo.education.course.service.biz.resp.CourseResp;
import com.roncoo.education.user.feign.interfaces.IFeignLecturer;
import com.roncoo.education.user.feign.interfaces.vo.LecturerViewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * API-课程信息
 *
 * @author wujing
 */
@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"course"})
public class ApiCourseBiz extends BaseBiz {

    @NotNull
    private final CourseDao dao;
    @NotNull
    private final CourseChapterDao chapterDao;
    @NotNull
    private final CourseChapterPeriodDao periodDao;
    @NotNull
    private final CategoryDao categoryDao;

    @NotNull
    private final IFeignLecturer feignLecturer;

    public Result<Page<ApiCoursePageResp>> searchForPage(ApiCoursePageReq req) {
        CourseExample example = new CourseExample();
        CourseExample.Criteria c = example.createCriteria();
        if (ObjectUtil.isNotEmpty(req.getCategoryId())) {
            c.andCategoryIdEqualTo(req.getCategoryId());
        }
        if (ObjectUtil.isNotEmpty(req.getIsFree())) {
            c.andIsFreeEqualTo(req.getIsFree());
        }
        if (StringUtils.hasText(req.getCourseName())) {
            c.andCourseNameLike(PageUtil.like(req.getCourseName()));
        }
        c.andStatusIdEqualTo(StatusIdEnum.YES.getCode());
        c.andIsPutawayEqualTo(PutawayEnum.UP.getCode());
        example.setOrderByClause("sort asc, id desc");
        Page<Course> page = dao.page(req.getPageCurrent(), req.getPageSize(), example);
        return Result.success(PageUtil.transform(page, ApiCoursePageResp.class));
    }

    private List<Long> listCategoryId(Long categoryId) {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andStatusIdEqualTo(StatusIdEnum.YES.getCode());
        List<Category> categories = categoryDao.listByExample(example);
        List<Long> idList = new ArrayList<>();
        // 需要查询的ID
        idList.add(categoryId);
        filter(idList, categories, categoryId);
        return idList;
    }

    private List<ApiCategoryResp> filter(List<Long> idList, List<Category> categories, Long categoryId) {
        List<Category> list = categories.stream().filter(item -> item.getParentId().compareTo(categoryId) == 0).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(list)) {
            idList.addAll(list.stream().map(Category::getId).collect(Collectors.toList()));
            List<ApiCategoryResp> resps = BeanUtil.copyProperties(list, ApiCategoryResp.class);
            for (ApiCategoryResp resp : resps) {
                resp.setList(filter(idList, categories, resp.getId()));
            }
            return resps;
        }
        return new ArrayList<>();
    }


    @Cacheable
    public Result<CourseResp> view(CourseReq req) {
        Course course = dao.getById(req.getCourseId());
        if (course == null) {
            return Result.error("找不到该课程信息");
        }
        if (!course.getStatusId().equals(StatusIdEnum.YES.getCode())) {
            return Result.error("该课程已被禁用");
        }
        if (course.getIsPutaway().equals(PutawayEnum.DOWN.getCode())) {
            return Result.error("该课程已下架");
        }
        CourseResp courseResp = BeanUtil.copyProperties(course, CourseResp.class);
        // 获取讲师信息
        LecturerViewVO lecturerViewVO = feignLecturer.getById(course.getLecturerId());
        if (ObjectUtil.isNotEmpty(lecturerViewVO)) {
            courseResp.setLecturerResp(BeanUtil.copyProperties(lecturerViewVO, CourseLecturerResp.class));
        }
        // 章节信息
        List<CourseChapter> chapterList = chapterDao.listByCourseIdAndStatusId(course.getId(), StatusIdEnum.YES.getCode());
        if (CollUtil.isNotEmpty(chapterList)) {
            courseResp.setChapterRespList(BeanUtil.copyProperties(chapterList, CourseChapterResp.class));
            // 课时信息
            List<CourseChapterPeriod> periodList = periodDao.listByCourseIdAndStatusId(course.getId(), StatusIdEnum.YES.getCode());
            if (CollUtil.isNotEmpty(periodList)) {
                Map<Long, List<CourseChapterPeriod>> map = periodList.stream().collect(Collectors.groupingBy(CourseChapterPeriod::getChapterId, Collectors.toList()));
                for (CourseChapterResp chapterResp : courseResp.getChapterRespList()) {
                    chapterResp.setPeriodRespList(BeanUtil.copyProperties(map.get(chapterResp.getId()), CourseChapterPeriodResp.class));
                }
            }
        }
        return Result.success(courseResp);
    }

}
