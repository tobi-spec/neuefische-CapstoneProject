import axios from 'axios'

const LoginPath = '/auth/access_token'
const FoodPath = '/api/food'

export const getToken = credentials =>
  axios
    .post(LoginPath, credentials)
    .then(response => response.data)
    .then(dto => dto.token)

const header = token => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
})

export const addFood = (food, token) =>
  axios.post(FoodPath, food, header(token))

export const getFood = token => axios.get(FoodPath, header(token))
