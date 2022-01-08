import './GameStyle.css';
import { useNavigate } from "react-router-dom";
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
            <img src="mainHang.png" width="100px" height="100px" className="img-fluid pb-3"></img>
            <h2 className="text-nowrap">Žaidimas Hangman</h2>
            <div className="d-flex flex-column pt-3">
                <div>
                <button type="button" className="btn btn-outline-primary" onClick={() => CheckConnection()}>Pradėti žaidimą</button>
                {serverAlive !== true &&
                  <p className="error-connect">Serverio klaida, bandykite dar karta po kelių sekundžių</p>
                }
                </div>
                <h4 className='pt-5 pb-3'>Instrukcijos:</h4>

                <p className="fw-bold text-wrap">Žodis turi būti atspėjamas per mažiau nei 10 bandymų</p>
                <p className="fw-bold">Jeigu bandote rašyti tą pačia raidę, užsiskaito kaip bandymas</p>
                <p className="fw-bold">Spėjamos raidės nebūtinai turi būti didžiosios arba mažosios</p>
             </div>
        </div>
    );
}
export default Menu