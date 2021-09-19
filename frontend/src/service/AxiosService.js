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

export const getFood = token =>
  axios.get(FoodPath, header(token)).then(response => response.data)

export const updateFood = (id, newName, token) =>
  axios.put(FoodPath + `/${id}`, newName, header(token))

export const removeFood = (id, token) =>
  axios.delete(FoodPath + `/${id}`, header(token))

export const addPain = (pain, token) =>
  axios.post(PainPath, pain, header(token))

export const getPain = token =>
  axios.get(PainPath, header(token)).then(response => response.data)

export const updatePain = (id, newNumber, token) =>
  axios.put(PainPath + `/${id}`, newNumber, header(token))

export const removePain = (id, token) =>
  axios.delete(PainPath + `/${id}`, header(token))
