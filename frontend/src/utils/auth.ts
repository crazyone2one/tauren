const ACCESS_TOKEN = 'accessToken'
const REFRESH_TOKEN = 'refreshToken'
/**
 * 获取token
 */
export const getToken = () => {
    return {
        [ACCESS_TOKEN]: localStorage.getItem(ACCESS_TOKEN),
        [REFRESH_TOKEN]: localStorage.getItem(REFRESH_TOKEN) || ''
    }
}
/**
 * 设置token
 */
export const setToken = (accessToken: string, refreshToken: string) => {
    localStorage.setItem(ACCESS_TOKEN, accessToken);
    localStorage.setItem(REFRESH_TOKEN, refreshToken);
}

export const cleanToken = () => {
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(REFRESH_TOKEN);
}

export const hasToken = () => {
    return !!localStorage.getItem(ACCESS_TOKEN) && !!localStorage.getItem(REFRESH_TOKEN);
};