import { createApp } from 'vue';
import App from './App.vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css'
import { createRouter, createWebHistory } from 'vue-router';
import HomePage from './components/HomePage.vue';
import OtherPage from './components/OtherPage.vue';

const routes = [
    { path: '/', component: HomePage },
    { path: '/other', component: OtherPage }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

createApp(App)
    .use(ElementPlus)
    .use(router)
    .mount('#app');
