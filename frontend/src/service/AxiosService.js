import axios from 'axios'

const loginPath = '/auth/access_token'
const foodPath = '/api/food'
const painPath = '/api/pain'
const createUserPath = "/api/user"


export const getToken = credentials =>
  axios
    .post(loginPath, credentials)
    .then(response => response.data)
    .then(dto => dto.token)

export const createUser = userDto =>
    axios.post(createUserPath, userDto)

const header = token => ({
  headers: {
    Authorization: `Bearer ${token}`,
  },
})

export const addFood = (food, token) =>
  axios.post(foodPath, food, header(token))

export const getFood = token =>
  axios.get(foodPath, header(token)).then(response => response.data)

export const updateFood = (id, newName, token) =>
  axios.put(foodPath + `/${id}`, newName, header(token))

export const removeFood = (id, token) =>
  axios.delete(foodPath + `/${id}`, header(token))

export const addPain = (pain, token) =>
  axios.post(painPath, pain, header(token))

export const getPain = token =>
  axios.get(painPath, header(token)).then(response => response.data)

export const updatePain = (id, newNumber, token) =>
  axios.put(painPath + `/${id}`, newNumber, header(token))

export const removePain = (id, token) =>
  axios.delete(painPath + `/${id}`, header(token))

