import {alovaInstance as request} from "/@/api/index.ts";
import {ICommonPage, IJob, ITableQueryParams} from "/@/api/types/common.ts";

export const fetchQuartzTaskPage = (page: number, pageSize: number, keyword: string) => {
    const params: ITableQueryParams = {page: page, pageSize: pageSize, keyword: keyword};
    return request.Post<ICommonPage<IJob>>("/quartz/page", params);
}
/**
 * 立即执行一次任务
 * @param params
 */
export const runTask = (params: IJob) => request.Put('/quartz/once', params)
/**
 * 创建任务
 * @param params
 */
export const createTask = (params: IJob) => request.Post('/quartz/create', params)
/**
 * 修改任务
 * @param params
 */
export const modifyTask = (params: IJob) => request.Put('/quartz/modify', params)
/**
 * 删除任务
 * @param params
 */
export const deleteTask = (params: IJob) => request.Post('/quartz/deleteJob', params);
/**
 * 暂停任务
 * @param params
 */
export const pauseTask = (params: IJob) => request.Post('/quartz/pause', params)
/**
 * 恢复任务
 * @param params
 */
export const resumeTask = (params: IJob) => request.Post('/quartz/resume', params)
export const changeTaskStatus = (taskId: string, taskStatus: string) => request.Put('/quartz/changeStatus', {
    id: taskId,
    status: taskStatus
})