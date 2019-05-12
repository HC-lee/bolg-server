package com.supcoder.blog.controller.admin;

import com.supcoder.blog.controller.BaseController;
import com.supcoder.blog.service.MetaService;
import com.supcoder.blog.util.JsonResult;
import com.supcoder.blog.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 属性(标签和分类)管理 Controller
 *
 * @author zbw
 * @since 2017/8/28 23:16
 */
@RestController
@RequestMapping("/api/admin/meta")
public class MetaController extends BaseController {

    @Autowired
    private MetaService metaService;

    /**
     * 获取所有属性
     *
     * @return {@see List<MetaDto>}
     */
    @GetMapping
    public JsonResult getAll(@RequestParam String type) {
        return ResultUtil.success(metaService.getMetaDtos(type));
    }

    /**
     * 根据name删除分类
     *
     * @param name 属性名
     * @param type 属性类型 {@see Types#CATEGORY},{@see Types#TAG}
     * @return {@see ResultUtil.success()}
     */
    @DeleteMapping
    public JsonResult deleteMeta(@RequestParam String name, @RequestParam String type) {
        if (metaService.deleteMeta(name, type)) {
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }

    /**
     * 添加一个分类
     *
     * @param name 属性名
     * @param type 属性类型 {@see Types#CATEGORY},{@see Types#TAG}
     * @return {@see ResultUtil.success()}
     */
    @PostMapping
    public JsonResult saveMeta(@RequestParam String name, @RequestParam String type) {
        if (metaService.saveMeta(name, type)) {
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }

    /**
     * 根据id修改分类
     *
     * @param id   属性id
     * @param name 新属性名
     * @param type 新属性类型
     * @return
     */
    @PostMapping("{id}")
    public JsonResult updateMeta(@PathVariable Integer id, @RequestParam String name, @RequestParam String type) {
        if (metaService.updateMeta(id, name, type)) {
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }
}
