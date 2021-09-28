import {useHistory} from "react-router-dom";
import Button from "./Button";

export default function ProfileButton(){
    const history = useHistory()

    const handleClick = () => {
        history.push("/profile")
    }

    return <Button onClick={handleClick}> Profile </Button>
}