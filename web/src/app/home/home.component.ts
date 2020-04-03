import { Component, OnInit } from '@angular/core';
import {StateService} from '../services/state.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(private stateService: StateService) { }

  ngOnInit() {
  }

}
