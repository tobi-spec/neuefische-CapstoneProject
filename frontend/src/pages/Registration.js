import styled from "styled-components";
import Header from "../components/Header";
import Content from "../components/Content";
import InputField from "../components/InputField";
import {useState} from "react";
import Button from "../components/Button";
import {createUser} from "../service/AxiosService";
import {Redirect} from "react-router-dom";
import {useAuth} from "../auth/AuthProvider";
import Footer from "../components/Footer";

export default function Registration() {
    const {user, login} = useAuth()
    const [error, setError] = useState()
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
            .then(() => login(credentials))
            .catch(error => setError(error))
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
                        placeholder="Username"
                        type="Text"
                        name="userName"
                        value={credentials.userName}
                        onChange={credentialsHandler }/>
                    <InputField
                        placeholder="Password"
                        type="Text"
                        name="userPassword"
                        value={credentials.userPassword}
                        onChange={credentialsHandler }/>
                    <Button>sign</Button>
                    {error && <p className="error">{error.response.data.message}</p>}
                </form>
            </Content>
            <Footer/>
        </Wrapper>
}

const Wrapper = styled.div`
  
  Form {
    grid-column: 2;
    grid-row: 2;
  }

  .error {
    grid-column: 2;
    grid-row: 3;
  }
`

