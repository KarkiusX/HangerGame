import './GameStyle.css';
import { API } from '../Api/Base'
import { Link, useNavigate } from "react-router-dom";
import { useState } from 'react';

function Menu()
{
    const [serverAlive, setAlive] = useState(true)

    let navigate = useNavigate();

    const CheckConnection = async () => {
        await fetch(process.env.REACT_APP_SERVER_IP + "/connect").then((res) => {
            navigate("game");
        })
        .catch(() => {
            setAlive(false);
        })
    
    }

    return(
        <div className="middle">
            <h2 className="text-nowrap">Žaidimas Hangman</h2>
            <div className="d-flex flex-column text-nowrap pt-3">
                <div>
                <button type="button" className="btn btn-outline-primary" onClick={() => CheckConnection()}>Pradėti žaidimą</button>
                {serverAlive !== true &&
                  <p className="error-connect">Serverio klaida, bandykite dar karta po kelių sekundžių</p>
                }
                </div>
                <h4 className='pt-5 pb-3'>Instrukcijos:</h4>

                <p className="fw-bold">Žodis turi būti atspėjamas per mažiau nei 10 bandymų</p>
                <p className="fw-bold">Jeigu bandote rašyti tą pačia raidę, užsiskaito kaip bandymas</p>
             </div>
        </div>
    );
}
export default Menu