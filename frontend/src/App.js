import { Route, BrowserRouter as Router, Switch } from 'react-router-dom'
import Main from './pages/Main'

function App() {
  return (
    <Router>
      <Switch>
        <Route path="/">
          <Main />
        </Route>
      </Switch>
    </Router>
  )
}

export default App
