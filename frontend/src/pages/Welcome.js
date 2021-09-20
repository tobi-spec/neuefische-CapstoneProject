import styled from "styled-components";
import {Link} from "react-router-dom";

export default function Welcome() {
    return<Wrapper>
            <h1 className="main-title">Intestinal Inspector</h1>
            <h1 className="sub-title">Welcome</h1>
            <ul>
                <li><Link className="link" to="/login">Login</Link></li>
                <li><Link className="link" to="/registration">Registration</Link></li>
            </ul>
            <img src="detectiv.png" alt="detective"/>
    </Wrapper>
}

const Wrapper = styled.div`
  display: grid;
  height: 100vh;
  grid-template-rows: 1fr 1fr 1fr 1fr 1fr 1fr;
  background-color: #138808;
  align-items: center;
  align-content: center;
  justify-content: center;
  justify-items: center;
  
  .main-title {
    grid-row: 1;
    font-size: 30px;
  }
  
  .link {
    color: black;
    text-decoration: none;
    font-size: 20px;
  }
  
  ul {
    list-style-type: circle;
  }
  
  img {
    grid-row: span 3;
    height: 280px;
  }
  
  
  
  
  
  
`
