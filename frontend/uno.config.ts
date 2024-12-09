import {defineConfig, presetAttributify, presetUno, transformerVariantGroup} from 'unocss'

export default defineConfig({
    // ...UnoCSS options
    presets: [presetUno({ dark: 'class' }), presetAttributify()],
    shortcuts: {
        'wh-full': 'w-full h-full',
        'flex-center': 'flex justify-center items-center',
        'flex-col-center': 'flex-center flex-col',
        'flex-x-center': 'flex justify-center',
        'flex-y-center': 'flex items-center',
    },
    transformers: [
        transformerVariantGroup(),
    ],
})