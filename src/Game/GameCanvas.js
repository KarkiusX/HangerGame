import { minHeight } from '@mui/system';
import React, { Fragment, useState } from 'react';
import { Table } from 'react-bootstrap';
import { API } from '../Api/Base'
import './GameStyle.css';

export class Game extends React.Component {

    constructor() {
        super();
        let head = <circle cx="50%" cy="20%" r="10%"/>;
        let spine = <line x1="50%" y1="30%" x2="50%" y2="70%" stroke="rgb(0, 0, 0)" stroke-width="5"/>
        let lefthand = <line x1="50%" y1="30%" x2="15%" y2="50%" stroke="rgb(0, 0, 0)" stroke-width="5"/>
        let righthand = <line x1="50%" y1="30%" x2="85%" y2="50%" stroke="rgb(0, 0, 0)" stroke-width="5"/>
        let leftleg = <line x1="50%" y1="70%" x2="35%" y2="90%" stroke="rgb(0, 0, 0)" stroke-width="5"/>;
        let rightleg = <line x1="50%" y1="70%" x2="65%" y2="90%" stroke="rgb(0, 0, 0)" stroke-width="5"/>
        let dead_man = [
            head,
            spine,
            lefthand,
            righthand,
            leftleg,
            rightleg
        ]
        this.state = { info: undefined, parts: dead_man, cells: []};

        this.submitData = this.submitData.bind(this);
        this.GetGameInfo = this.GetGameInfo.bind(this);
      }
    async componentDidMount()
    {
       await API.get("/game").then( r => {
        this.setState({info : r.data});
        this.GetGameInfo(r);
       });

    }
    async submitData(event)
    {
        event.preventDefault();

        let word = event.target[0].value;
        
        await API.put("/game/" + word).then((res) => {
            this.setState({info : res.data});
            this.GetGameInfo(res);
        })

    }
    GetGameInfo(res)
    {

        let cells = [];
        for(var i = 0; i < res.data.Word.length; i++)
        {
            if(res.data.Word[i] !== '-')
            {
                let cellWord = (<div className="d-inline border cell">
                <h2 className="text-center align-middle">{res.data.Word[i]}</h2>
                </div>)
                cells.push(cellWord)
            }
            else
            {
                let cellEmpty = (<div className="d-inline border cell">
                <h2 className="text-center align-middle"></h2>
                </div>)
                cells.push(cellEmpty)
            }
                
        }
        this.setState({cells : cells})
    }

    render()
    {
        if(this.state.info !==  undefined)
        {
            console.log(this.state.info);
        }

        return (
            <div>
            {this.state.info !== undefined && 
                <div className="pt-5 text-center">
                    <h5>{this.state.info.Description}</h5>
                    <svg className="pt-5" width="300px" height="350px">
                        <line x1="0%" y1="0%" x2="0%" y2="100%" stroke="rgb(0, 0, 0)" stroke-width="15"/>
                        <line x1="0%" y1="0%" x2="50%" y2="0%" stroke="rgb(0, 0, 0)" stroke-width="15"/>
                        <line x1="50%" y1="0%" x2="50%" y2="30%" stroke="rgb(0, 0, 0)" stroke-width="6"/>
                        <BuildMan mistakes={this.state.info.Guesses} parts={this.state.parts}/>
                    </svg>
                    <div className="d-flex justify-content-center pt-5 pb-5 w-80">
                        {this.state.cells.map(item =>{
                            return item
                        })}
                    </div>
                    <form onSubmit={this.submitData}>
                        <div className="col-3">
                            <input className="w-30 form-control mb-2 input-center" placeholder="Įveskite spėjama raidę" maxLength="1"></input>  
                        </div>
                        <button type="submit" className="btn btn-outline-dark">Pateikti</button>
                    </form>
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