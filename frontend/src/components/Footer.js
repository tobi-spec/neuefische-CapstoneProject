import styled from 'styled-components'
import { useAuth } from '../auth/AuthProvider'
import { NavLink } from 'react-router-dom'

export default function Footer() {

  return (
    <Wrapper>
        <NavLink className="Link" to="/main">
          Notes
        </NavLink>
        <NavLink className="Link" to="/fooddiary">
          Food Diary
        </NavLink>
        <NavLink className="Link" to="/paindiary">
          Pain Diary
        </NavLink>
          <NavLink className="Link" to="/tracker">
            Perpetrator Tracker
          </NavLink>
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
