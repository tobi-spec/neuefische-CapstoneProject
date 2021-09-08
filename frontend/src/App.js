import { Route, BrowserRouter as Router, Switch } from 'react-router-dom'
import Main from './pages/Main'
import Login from './pages/Login'

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/">
          <Login />
        </Route>
        <Route path="/main">
          <Main />
        </Route>
      </Switch>
    </Router>
  )
}

export default App
