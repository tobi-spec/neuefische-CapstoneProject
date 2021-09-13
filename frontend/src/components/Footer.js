import styled from 'styled-components'
import { useAuth } from '../auth/AuthProvider'
import { NavLink } from 'react-router-dom'

export default function Footer() {
  const { user } = useAuth()

  return (
    <Wrapper>
      {user && (
        <NavLink className="Link" to="/main">
          Main
        </NavLink>
      )}
      {user && (
        <NavLink className="Link" to="/fooddiary">
          {' '}
          Food Diary
        </NavLink>
      )}
    </Wrapper>
  )
}

const Wrapper = styled.footer`
  background-color: #138808;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;

  .Link {
    font-size: 20px;
    text-decoration: none;
    color: black;
    margin: 5px;
    padding: 10px;
  }
`
