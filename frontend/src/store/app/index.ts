import {defineStore} from "pinia";
import {IAppState} from "/@/store/app/types.ts";

const useAppStore = defineStore('app', {
    state: (): IAppState => {
        return {
            innerHeight: 0,
        };
    },
})
export default useAppStore;