import styled from "styled-components";
import Header from "../components/Header";
import Content from "../components/Content";
import InputField from "../components/InputField";
import {useState} from "react";
import Button from "../components/Button";
import {createUser} from "../service/AxiosService";
import {Redirect} from "react-router-dom";
import {useAuth} from "../auth/AuthProvider";

export default function Registration() {
    const {user, login} = useAuth()
    const [credentials, setCredentials] = useState({
        userName: "",
        userPassword: "",
    })

    const credentialsHandler  = event => {
        setCredentials({...credentials, [event.target.name]: event.target.value});
    }

    const signSubmitHandler = event => {
        event.preventDefault()
        createUser(credentials)
            .then(response => console.log(response))
            .catch(error => console.error(error))
            .then(() => login(credentials))
            .catch(error => console.error(error))
            .finally(() => setCredentials({
                userName: "",
                userPassword: "",
            }))
    }

    if (user) {
        return <Redirect to="/main" />
    }

    return <Wrapper>
            <Header title="Registration"/>
            <Content>
                <form onSubmit={signSubmitHandler}>
                    <InputField
                        title="Username"
                        type="Text"
                        name="userName"
                        value={credentials.userName}
                        onChange={credentialsHandler }/>
                    <InputField
                        title="Password"
                        type="Text"
                        name="userPassword"
                        value={credentials.userPassword}
                        onChange={credentialsHandler }/>
                    <Button>sign</Button>
                </form>
            </Content>
        </Wrapper>
}

const Wrapper = styled.div`
  
  Form {
    grid-column: 2;
    grid-row: 2;
  }
`

