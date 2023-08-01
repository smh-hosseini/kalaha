import { createApp } from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import Board from './components/Board.vue'
import GameEntryPage from './components/GameEntryPage.vue';

import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'

Vue.config.productionTip = false

Vue.use(VueRouter);

const routes = [
  { path: '/', component: GameEntryPage },
  { path: '/board', component: Board }, 
];

const router = new VueRouter({
  routes,
});


createApp(App).router(router)..mount('#app')
