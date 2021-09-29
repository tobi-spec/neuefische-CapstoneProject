import styled from "styled-components";
import Header from "../components/Header";
import Content from "../components/Content";
import Footer from "../components/Footer";
import ResetPassword from "../components/ResetPassword";
import DeleteAccount from "../components/DeleteAccount";
import Button from "../components/Button";
import {useState} from "react";

export default function Profile(){
    const [reset, setReset] = useState(false)
    const [remove, setRemove ] = useState(false)

    const resetHandler = () => {
        setReset(true)
    }

    const removeHandler = () => {
        setRemove(true)
    }

    const cancelHandler = () => {
        setReset(false)
        setRemove(false)
    }

    return (<Wrapper>
        <Header title="Profile"/>
        <Content className="content">
            <div className="bnt-1">
            {reset && <ResetPassword className="bnt-1" cancelHandler={cancelHandler}/>}
            {remove && <DeleteAccount cancelHandler={cancelHandler}/>}
            </div>
            {!reset && !remove && <Button className="bnt-1" onClick={resetHandler}>reset password</Button>}
            {!reset && !remove && <Button className="bnt-2" onClick={removeHandler}>remove account</Button>}
        </Content>
        <Footer/>
    </Wrapper>)
}

const Wrapper = styled.div`

    .bnt-1{
      grid-column: 2;
      grid-row: 2;
    }
  
    .bnt-2{
        grid-column: 2;
        grid-row: 3;
    }
  
`