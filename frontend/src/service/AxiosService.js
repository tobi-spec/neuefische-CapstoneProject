import axios from 'axios'

export const getToken = credentials =>
  axios
    .post('auth/access_token', credentials)
    .then(response => response.data)
    .then(dto => dto.token)

const header = token => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
})

export const addFood = (food, token) =>
  axios.post('/api/food', food, header(token))

export const getFood = token => axios.get('/api/food', header(token))
