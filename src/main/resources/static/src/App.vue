<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <Board />
</template>

<script>
import Board from './components/Board.vue'
import GameEntryPage from './components/GameEntryPage.vue';

export default {
  name: 'App',
  components: {
    Board,
    VueRouter,
    GameEntryPage
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
