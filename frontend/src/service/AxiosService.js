import axios from 'axios'

const LoginPath = '/auth/access_token'
const FoodPath = '/api/food'
const PainPath = '/api/pain'

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

export const updateFood = (newName, token) =>
  axios.put(FoodPath, newName, header(token))

export const addPain = (pain, token) =>
  axios.post(PainPath, pain, header(token))

export const getPain = token => axios.get(PainPath, header(token))
