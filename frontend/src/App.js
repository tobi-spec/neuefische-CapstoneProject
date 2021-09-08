import { Route, BrowserRouter as Router, Switch } from 'react-router-dom'
import Main from './pages/Main'
import Login from './pages/Login'
import ProtectedRoute from './auth/ProtectedRoute'

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/" component={Login} />
        <ProtectedRoute path="/main" component={Main} />
      </Switch>
    </Router>
  )
}

export default App
