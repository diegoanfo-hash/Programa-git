import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// GA7-220501096-AA4-EV02
// Autor: Diego Andrés Fonseca
export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    // Redirigir peticiones /api al backend Spring Boot
    proxy: {
      '/api': 'http://localhost:8080'
    }
  }
});
