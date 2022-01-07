import React from 'react';
import { API } from '../Api/Base'
import './GameStyle.css';

export class Game extends React.Component {

    constructor() {
        super();
        let deadParts = CreateParts();
        this.state = { info: undefined, parts: deadParts, cells: []};
        this.submitData = this.submitData.bind(this);
        this.GetGameInfo = this.GetGameInfo.bind(this);
        this.StartGame = this.StartGame.bind(this);
        this.reload = this.reload.bind(this);
        this.RemoveGame = this.RemoveGame.bind(this);

      }
    reload(e)
    {
        this.RemoveGame();
    }
    async RemoveGame()
    {
        let gameId = localStorage.getItem("gameId");
        var data = new Object();
        data.gameId = gameId;
        var dataJson = JSON.stringify(data);
        await API.put("/disconnect/" + dataJson).then(() => {
            localStorage.removeItem("gameId");
        })
    }
    async componentDidMount()
    {
        window.addEventListener("beforeunload", this.reload)
        this.StartGame();
    }
    async componentWillUnmount()
    {
        window.removeEventListener("beforeunload", this.reload);
        this.RemoveGame();
    }
    async submitData(event)
    {
        event.preventDefault();

        let word = event.target[0].value;

        let gameid = localStorage.getItem("gameId");

        if(word.length < 1)
          return;

        var data = new Object();
        data.gameId = gameid;
        data.guessingLetter = word;

        var dataJson = JSON.stringify(data);

        console.log(dataJson);

        await API.put("/game", dataJson).then((res) => {
            this.setState({info : res.data});
            this.GetGameInfo(res.data);
        })

    }
    async StartGame()
    {
        await API.post("/game").then( r => {
            localStorage.setItem("gameId", r.data.gameId);
            this.setState({info : r.data});
            this.GetGameInfo(r.data);
           }).catch(e => {
               window.alert("Serveris turi laikinu problemų! Bandykite prisijungti vėliau")
           })
    }
    GetGameInfo(res)
    {
        let cells = [];
        let gameState = res.GameState;
        for(var i = 0; i < res.Word.length; i++)
        {
            let word = res.Word[i];
            if(gameState === "Won")
            {
                let cellWord = (<div key={i} className="d-inline border cell">
                <h2 className="text-center align-middle won-display">{word}</h2>
                </div>)
                cells.push(cellWord)
            }
            else if(word !== '-')
            {
                let cellWord = (<div key={i} className="d-inline border cell">
                <h2 className="text-center align-middle">{word}</h2>
                </div>)
                cells.push(cellWord)
            }
            else
            {
                if(gameState === "Lost")
                {
                    word = res.data.cw[i];
                    let cellEmpty = (<div key={i} className="d-inline border cell">
                    <h2 className="text-center align-middle wrong-word">{word}</h2>
                    </div>)
                    cells.push(cellEmpty)
                }
                else
                {
                    let cellEmpty = (<div key={i} className="d-inline border cell">
                    <h2 className="text-center align-middle"> </h2>
                    </div>)
                    cells.push(cellEmpty)
                }
            }
                
        }
        this.setState({cells : cells})
    }

    render()
    {
        let gameState;
        if(this.state.info !== undefined)
            gameState = this.state.info.GameState;

        return (
            <div>
            {this.state.info !== undefined && 
                <div className="pt-5 text-center">
                    <div>
                    <p className='d-inline'>Užuomina: </p>
                    <h5 className='d-inline'>{this.state.info.Description}</h5>
                    </div>
                    <svg className="pt-5" width="300px" height="350px">
                        <line x1="0%" y1="0%" x2="0%" y2="100%" stroke="rgb(0, 0, 0)" strokeWidth="15"/>
                        <line x1="0%" y1="0%" x2="50%" y2="0%" stroke="rgb(0, 0, 0)" strokeWidth="15"/>
                        <line x1="50%" y1="0%" x2="50%" y2="30%" stroke="rgb(0, 0, 0)" strokeWidth="6"/>
                        <BuildMan mistakes={this.state.info.ig} parts={this.state.parts}/>
                    </svg>
                    <div className="d-flex justify-content-center pt-5 pb-5 w-80">
                        {this.state.cells.map(item =>{
                            return item
                        })}
                    </div>
                    {gameState === "Started" && <form onSubmit={this.submitData}>
                        <label htmlFor= "submit" className="d-inline"><p className="d-inline">Bandytos raidės: </p><p className="last-words d-inline">{this.state.info.LettersGuessed}</p></label>
                        <div className="col-3" id="submit">
                            <input className="w-30 form-control mb-2 input-center" placeholder="Įveskite spėjama raidę" maxLength="1" required></input>  
                        </div>
                        <button type="submit" className="btn btn-outline-dark">Pateikti</button>
                    </form>
                    }
                    {gameState === "Won" && <div>
                        <h4>Sveikiname, išvengiatė kartuvių!</h4>
                        <button type="button" className="btn btn-outline-dark" onClick={this.StartGame}>Žaisti per naujo</button>
                    </div>}
                    {gameState === "Lost" && <div>
                        <h4>Nepavyko išvengti kartuvių!</h4>
                        <button type="button" className="btn btn-outline-dark" onClick={this.StartGame}>Žaisti per naujo</button>
                    </div>}
                 </div>
            }
            </div>
        )
    }
}
function BuildMan(props)
{
    let alive_man = [];
    for (var i = 0; i < props.mistakes; i++) {
       alive_man.push(props.parts[i]);
    }
    return alive_man;
}
function CreateParts()
{
    let head = <circle cx="50%" cy="20%" r="10%"/>;
    let spine = <line x1="50%" y1="30%" x2="50%" y2="70%" stroke="rgb(0, 0, 0)" strokeWidth="5"/>
    let lefthand = <line x1="50%" y1="30%" x2="15%" y2="50%" stroke="rgb(0, 0, 0)" strokeWidth="5"/>
    let righthand = <line x1="50%" y1="30%" x2="85%" y2="50%" stroke="rgb(0, 0, 0)" strokeWidth="5"/>
    let leftleg = <line x1="50%" y1="70%" x2="35%" y2="90%" stroke="rgb(0, 0, 0)" strokeWidth="5"/>;
    let rightleg = <line x1="50%" y1="70%" x2="65%" y2="90%" stroke="rgb(0, 0, 0)" strokeWidth="5"/>
    let leftEye =  [
                   <line x1="43%" y1="20%" x2="46%" y2="15%" stroke="rgb(255, 0, 0)"strokeWidth="5"/>,
                   <line x1="46%" y1="20%" x2="43%" y2="15%" stroke="rgb(255, 0, 0)" strokeWidth="5"/>
                    ]
    let rightEye =  [
        <line x1="53%" y1="20%" x2="56%" y2="15%" stroke="rgb(255, 0, 0)" strokeWidth="5"/>,
        <line x1="56%" y1="20%" x2="53%" y2="15%" stroke="rgb(255, 0, 0)" strokeWidth="5"/>
            ]
    let nose = <line x1="50%" y1="20%" x2="50%" y2="25%" stroke="rgb(255, 0, 0)" strokeWidth="5"/>
    let mouth = <line x1="48%" y1="28%" x2="52%" y2="28%" stroke="rgb(255, 0, 0)" strokeWidth="5"/>
    let dead_man = [
        head,
        spine,
        lefthand,
        righthand,
        leftleg,
        rightleg,
        leftEye,
        rightEye,
        nose,
        mouth
    ]
    return dead_man;
}