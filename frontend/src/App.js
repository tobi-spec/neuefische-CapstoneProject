import { Route, BrowserRouter as Router, Switch } from 'react-router-dom'
import Main from './pages/Main'
import Login from './pages/Login'
import ProtectedRoute from './auth/ProtectedRoute'
import AuthProvider from './auth/AuthProvider'
import FoodDiary from './pages/FoodDiary'

function App() {
  return (
    <AuthProvider>
      <Router>
        <Switch>
          <Route exact path="/" component={Login} />
          <ProtectedRoute path="/main" component={Main} />
          <ProtectedRoute path="/fooddiary" component={FoodDiary} />
        </Switch>
      </Router>
    </AuthProvider>
  )
}

export default App
