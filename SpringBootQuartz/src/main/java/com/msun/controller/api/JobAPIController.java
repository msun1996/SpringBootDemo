package com.msun.controller.api;

import com.msun.common.CommonResponse;
import com.msun.common.ResponseUtil;
import com.msun.domain.ScheduleJob;
import com.msun.service.api.JobAPIService;
import com.msun.common.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/job")
public class JobAPIController {

    @Autowired
    private JobAPIService jobAPIService;

    /**
     * 获取所有定时任务
     * @return
     */
    @GetMapping
    public CommonResponse getAllJob() {
        return ResponseUtil.generateResponse(jobAPIService.getAllJob());
    }

    /**
     * 根据id查找任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    @GetMapping("/{id:\\d+}")
    public CommonResponse getJob(@PathVariable("id") int jobId) throws ServiceException {
        return ResponseUtil.generateResponse(jobAPIService.getById(jobId));
    }

    /**
     * 根据id查找并修改定时任务
     * @param jobId
     * @param newScheduleJob
     * @return
     * @throws ServiceException
     */
    @PutMapping("/update/{id:\\d+}")
    public CommonResponse updateJob(@PathVariable("id") int jobId, @RequestBody ScheduleJob newScheduleJob) throws ServiceException {
        return ResponseUtil.generateResponse(jobAPIService.modify(jobId, newScheduleJob));
    }

    /**
     * 根据id删除定时任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    @DeleteMapping("/{id:\\d+}")
    public CommonResponse deleteJob(@PathVariable("id") int jobId) throws ServiceException {
        return ResponseUtil.generateResponse(jobAPIService.deleteByID(jobId));
    }

    /**
     * 创建定时任务
     * @param newScheduleJob
     * @return
     * @throws ServiceException
     */
    @PostMapping()
    public CommonResponse saveJob(@RequestBody ScheduleJob newScheduleJob) throws ServiceException {
        return ResponseUtil.generateResponse(jobAPIService.add(newScheduleJob));
    }

    /**
     * 根据id启动定时任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    @PutMapping("/run/{id:\\d+}")
    public CommonResponse runJob(@PathVariable("id") int jobId) throws ServiceException {
        return ResponseUtil.generateResponse(jobAPIService.run(jobId));
    }

    /**
     * 根据id暂停定时任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    @PutMapping("/pause/{id:\\d+}")
    public CommonResponse pauseJob(@PathVariable("id") int jobId) throws ServiceException {
        return ResponseUtil.generateResponse(jobAPIService.pause(jobId));
    }

    /**
     * 根据id恢复定时任务
     * @param jobId
     * @return
     * @throws ServiceException
     */
    @PutMapping("/resume/{id:\\d+}")
    public CommonResponse resumeJob(@PathVariable("id") int jobId) throws ServiceException {
        return ResponseUtil.generateResponse(jobAPIService.resume(jobId));
    }
}
