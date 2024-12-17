export interface IPaginationInfo {
    startIndex?: number
    endIndex?: number
    page: number
    pageSize: number
    pageCount: number
    itemCount?: number | undefined
    simple?: boolean;
    total:number
}