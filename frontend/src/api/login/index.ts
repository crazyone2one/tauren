import {alovaInstance as request} from "/@/api/index.ts";
import {UserState} from "/@/store/auth/types.ts";
import CommonResponse from "/@/api/types/common.ts";


interface ILogin {
    username: string
    password: string
}

interface ILoginRes extends UserState {
    access_token: string;
    refresh_token: string;
}

export const fetchLogin = (params: ILogin) => {
    const method = request.Post<CommonResponse<ILoginRes>>("/api/v1/auth/authenticate", params);
    method.meta = {
        ignoreToken: true, authRole: null,
    };
    return method
}

export const fetchUpdateToken = (refreshToken: string) => {
    const method = request.Post<ILoginRes>("/api/v1/auth/refresh-token", {refreshToken});
    method.meta = {
        authRole: 'refreshToken',
    };
    return method
}