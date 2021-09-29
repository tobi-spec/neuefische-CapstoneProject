import styled from 'styled-components'

export default styled.button`
  background-color: #138808;
  display:inline-block;
  padding:0.35em 1.2em;
  border:0.1em solid black;
  margin:0 0.3em 0.3em 0;
  border-radius:0.12em;
  box-sizing: border-box;
  text-decoration:none;
  font-weight:300;
  color: black;
  text-align:center;
  transition: all 0.2s;

  :hover{
    color:#000000;
    background-color:#FFFFFF;
  }

  @media all and (max-width:30em){
      display:block;
      margin:0.4em auto;
      }
`
