import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/v1/game'; // Replace with your actual API base URL

export const createGame = async (createJoinGameRequest) => {
  // Mocked response with setTimeout (Replace with actual API call)
  return new Promise((resolve) => {
    setTimeout(() => {
      const mockResponse = { id: '123456' };
      resolve(mockResponse);
    }, 1000);
  });
};

export const getGame = async (gameId) => {
  // Mocked response with setTimeout (Replace with actual API call)
  return new Promise((resolve) => {
    setTimeout(() => {
      const mockResponse = {
        playerPits: {
          'ONE': [{ seeds: 6, index: 0 }, { seeds: 6, index: 0 }],
          'TWO': [{ seeds: 6, index: 0 }, { seeds: 6, index: 0 }],
        },
        players: [
          { name: 'Player 1', label: 'ONE' },
          { name: 'Player 2', label: 'TWO' },
        ],
        stores: {
          'ONE': { seeds: 6, index: 0 },
          'TWO': { seeds: 6, index: 0 },
        },
        turn: 'ONE',
        status: 'WAITING_FOR_PLAYER_TWO',
      };
      resolve(mockResponse);
    }, 1000);
  });
};

export const joinGame = async (gameId, createJoinGameRequest) => {
  // Mocked response with setTimeout (Replace with actual API call)
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve();
    }, 1000);
  });
};

export const play = async (gameId, move) => {
  // Mocked response with setTimeout (Replace with actual API call)
  return new Promise((resolve) => {
    setTimeout(() => {
      const mockResponse = {
        playerPits: {
          'ONE': [{ seeds: 6, index: 0 }, { seeds: 6, index: 0 }],
          'TWO': [{ seeds: 6, index: 0 }, { seeds: 6, index: 0 }],
        },
        players: [
          { name: 'Player 1', label: 'ONE' },
          { name: 'Player 2', label: 'TWO' },
        ],
        stores: {
          'ONE': { seeds: 6, index: 0 },
          'TWO': { seeds: 6, index: 0 },
        },
        turn: 'ONE',
        status: 'WAITING_FOR_PLAYER_TWO',
      };
      resolve(mockResponse);
    }, 1000);
  });
};


export default new KalahaGameService();