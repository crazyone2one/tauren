import {defineConfig, presetAttributify, presetUno, transformerVariantGroup, presetIcons} from 'unocss'
import fs from 'node:fs/promises'

export default defineConfig({
    // ...UnoCSS options
    presets: [presetUno({dark: 'class'}), presetAttributify(),
        presetIcons({
            collections: {
                'my-icons': {
                    question: () => fs.readFile('./src/assets/QuestionCircle.svg','utf-8')
                }
            }
        })],
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