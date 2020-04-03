import {Component, Input, OnInit} from '@angular/core';
import {Resource} from '../../models/resource';
import {StateService} from '../../services/state.service';


@Component({
  selector: 'app-resource-delete-button',
  templateUrl: './resource-delete-button.component.html',
  styleUrls: ['./resource-delete-button.component.css']
})
export class ResourceDeleteButtonComponent implements OnInit {

  constructor(private stateService: StateService) { }

  @Input() parent: Resource;

  ngOnInit(): void {
  }
  deleteResource($event){
    this.stateService.deleteResource(this.parent);
  }
}
