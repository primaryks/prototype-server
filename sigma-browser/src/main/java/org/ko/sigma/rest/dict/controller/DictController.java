package org.ko.sigma.rest.dict.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.ko.sigma.core.support.Response;
import org.ko.sigma.core.type.SystemCode;
import org.ko.sigma.data.entity.Dict;
import org.ko.sigma.rest.dict.condition.QueryDictCondition;
import org.ko.sigma.rest.dict.dto.DictDTO;
import org.ko.sigma.rest.dict.service.DictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "字典表，如果条件允许，可以放一部分进入缓存接口")
@RestController
@RequestMapping("dict")
@Validated
public class DictController {

    /**
     * 字典表，如果条件允许，可以放一部分进入缓存service
     */
    @Autowired private DictService dictService;

    @GetMapping
    @ApiOperation("查询全部字典表，如果条件允许，可以放一部分进入缓存")
    public Response<List<DictDTO>> queryDictList(@ApiParam("字典表，如果条件允许，可以放一部分进入缓存查询参数") @ModelAttribute QueryDictCondition condition) {
        //1. 查询字典表，如果条件允许，可以放一部分进入缓存列表数据
        List<Dict> dicts = dictService.queryDictList(condition);

        //2. 如果不为空
        if (CollectionUtils.isNotEmpty(dicts)) {
            List<DictDTO> dictDTOS = dicts.stream().map(this::map).collect(Collectors.toList());
            return new Response<>(dictDTOS);
        }
        return new Response<>(SystemCode.EMPTY_DATA);
    }

    @GetMapping("{id:\\d+}")
    @ApiOperation("通过ID查询字典表，如果条件允许，可以放一部分进入缓存")
    public Response<DictDTO> queryDictInfo (@ApiParam("主键") @PathVariable Long id) {
        Dict dict = dictService.queryDictInfo(id);
        return new Response<>(map(dict));
    }

    @PostMapping
    @ApiOperation("新增字典表，如果条件允许，可以放一部分进入缓存")
    public Response<Long> createDict (
            @ApiParam("字典表，如果条件允许，可以放一部分进入缓存传输对象实体") @RequestBody DictDTO dictDTO) {
        Long dictId = dictService.createDict(map(dictDTO));;
        return new Response<>(dictId);
    }

    @PutMapping("{id:\\d+}")
    @ApiOperation("修改字典表，如果条件允许，可以放一部分进入缓存")
    public Response<DictDTO> updateDict (
            @ApiParam("字典表，如果条件允许，可以放一部分进入缓存ID主键") @PathVariable Long id,
            @ApiParam("字典表，如果条件允许，可以放一部分进入缓存传输对象实体") @RequestBody DictDTO dictDTO) {
        Dict dict = dictService.updateDict(id, map(dictDTO));
        return new Response<>(map(dict));
    }

    @DeleteMapping("{id:\\d+}")
    @ApiOperation("删除字典表，如果条件允许，可以放一部分进入缓存")
    public Response<Long> deleteDict(@ApiParam("用户ID主键") @PathVariable Long id) {
        Long result = dictService.deleteDict(id);
        return new Response<>(result);
    }

    /**
     * Dict mapTo DictDTO
     * @param dict
     * @return dictDTO
     */
    private DictDTO map (Dict dict) {
        DictDTO dictDTO = new DictDTO();
        BeanUtils.copyProperties(dict, dictDTO);
        return dictDTO;
    }

    /**
     * DictDTO mapTo Dict
     * @param dictDTO
     * @return
     */
    private Dict map (DictDTO dictDTO) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictDTO, dict);
        return dict;
    }

}