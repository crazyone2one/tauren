import {alovaInstance as request} from "/@/api/index.ts";
import {ICommonPage, IJob, ITableQueryParams} from "/@/api/types/common.ts";

export const fetchQuartzTaskPage = (params: ITableQueryParams) => {
    return request.Post<ICommonPage<IJob>>("/quartz/page", params);
}
export const runTask = (params: IJob) => request.Put('/quartz/once', params)
export const createTask = (params: IJob) => request.Post('/quartz/create', params)