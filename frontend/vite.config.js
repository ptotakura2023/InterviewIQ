import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react(),
    tailwindcss(),
  ],

  test: {
    environment: 'jsdom',        // Simulates a browser environment
    globals: true,               // No need to import describe/it/expect in every file
    setupFiles: './src/tests/setup.js',  // Runs before every test
  },
})
