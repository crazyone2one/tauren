/// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_APP_TITLE: string;
  readonly VITE_APP_BASE_URL: string;
  readonly VITE_APP_PROXY_URL: string;
  readonly VITE_APP_BASE_API: string;
  // more env variables...
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}

declare module "*.vue" {
  import type {
    DialogProviderInst,
    MessageProviderInst,
    NotificationProviderInst,
  } from "naive-ui";
  import { DefineComponent } from "vue";
  global {
    interface Window {
      $message: MessageProviderInst;
      $dialog: DialogProviderInst;
      $notification: NotificationProviderInst;
    }
  }
  const component: DefineComponent;
  export default component;
}
