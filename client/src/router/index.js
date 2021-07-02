import { createRouter, createWebHistory } from 'vue-router'
import Home from "@/views/Home";

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register')
  },
  {
    path: '/user/:username',
    name: 'User',
    component: () => import('../views/User')
  },
  {
    path: '/clip/:clipId',
    name: 'Clip',
    component: () => import('../views/SingleClip')
  },
  {
    path: '/create',
    name: 'Create',
    component: () => import('../views/Create')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
