<template>
    <div>
      <label for="playerName">Player Name:</label>
      <input type="text" id="playerName" v-model="playerName" />
  
      <label for="gameId">Game ID (Optional):</label>
      <input type="text" id="gameId" v-model="gameId" />
  
      <button @click="handleGameEntry">Start Game</button>
    </div>
  </template>
  
  <script>
  import { createGame, joinGame } from '../services/KalahaGameService';
  
  export default {
    data() {
      return {
        playerName: '',
        gameId: '',
      };
    },
    methods: {
      async handleGameEntry() {
        if (this.gameId) {
          // Join existing game
          const createJoinGameRequest = {
            playerName: this.playerName
          };
  
          try {
            await joinGame(this.gameId, createJoinGameRequest);
            this.navigateToBoard();
          } catch (error) {
            console.error('Error joining game:', error);
            // Handle errors if necessary
          }
        } else {
          // Create new game
          const createJoinGameRequest = {
            playerName: this.playerName
            // Add other required data for creating the game if needed
          };
  
          try {
            const gameId = await createGame(createJoinGameRequest);
            this.navigateToBoard();
          } catch (error) {
            console.error('Error creating game:', error);
          }
        }
      },
      navigateToBoard() {
        // Replace 'BoardPage' with the actual name of the component representing the game board
        this.$router.push('/board'); // Navigate to the board page
      },
    },
  };
  </script>
  