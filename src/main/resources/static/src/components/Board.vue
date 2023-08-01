<template>
    <div class="game-board">
      <!-- Players -->
      <div class="players">
        <h2>Players:</h2>
        <ul>
          <li v-for="player in players" :key="player.id">{{ player.name }}</li>
        </ul>
      </div>
      <!-- Player Pits -->
      <div class="player-pits">
        <h2>Player Pits:</h2>
        <div v-for="(pits, player) in playerPits" :key="player" class="player-pits-group">
          <h3>{{ player }} Pits:</h3>
          <ul>
            <li v-for="pit in pits" :key="pit.id">{{ pit.name }}</li>
          </ul>
        </div>
      </div>
      <!-- Stores -->
      <div class="stores">
        <h2>Stores:</h2>
        <ul>
          <li v-for="(store, player) in stores" :key="player">{{ player }} Store: {{ store.name }}</li>
        </ul>
      </div>
      <!-- Status -->
      <div class="status">
        <h2>Status:</h2>
        <p>Game Status: {{ status }}</p>
      </div>
      <!-- Turn -->
      <div class="turn">
        <h2>Turn:</h2>
        <p>Current Player's Turn: {{ turn }}</p>
      </div>
    </div>
</template>

<script>

import KalahaGameService from '../services/KalahaGameService';

export default {
    name: 'Board',
    props: {
        players: {
        type: Array,
        default: () => []
        },
        playerPits: {
        type: Object,
        default: () => ({})
        },
        stores: {
        type: Object,
        default: () => ({})
        },
        status: {
        type: String,
        default: ''
        },
        turn: {
        type: String,
        default: ''
        }
    },
    data(){
        return {
            gameData: {}
        }
    },
    methods: {
        getGame(id){
            KalahaGameService.getGame(id).then((response) => {
                this.gameData = response.data;   
            });
        }
    },
    created() {
        this.getGame();
    }
}

</script>