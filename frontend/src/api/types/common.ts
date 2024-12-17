export default interface CommonResponse<T> {
    code: number;
    message: string;
    messageDetail: string;
    data: T;
}

export interface ICommonPage<T> {
    [x: string]: any;

    pageSize: number;
    total: number;
    current: number;
    records: T[];
}

export interface ITableQueryParams {
    // 当前页
    page?: number;
    // 每页条数
    pageSize?: number;
    // 查询条件
    keyword?: string;

    [key: string]: any;
}

export interface IJob {
    id?: string;
    jobName: string;
    jobGroup: string;
    cronExpression: string;
    param: string;
    jobClass: string;
    misfirePolicy: string;
    concurrent: string;
    status: string;
    cronJob: boolean
}
