import {AfterViewInit, Component, ElementRef, EventEmitter, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {StateService} from '../../services/state.service';
import {BehaviorSubject, fromEvent, Observable, Subscription} from 'rxjs';
import {distinctUntilChanged, map, tap} from 'rxjs/operators';

@Component({
  selector: 'app-relationship-button',
  templateUrl: './relationship-button.component.html',
  styleUrls: ['./relationship-button.component.css']
})
export class RelationshipButtonComponent implements OnInit, AfterViewInit, OnDestroy  {
  isRelated$: Observable<string>;
  buttonStream: Subscription;
  buttonText: string;
  // @ts-ignore
  @ViewChild('button') button;

  constructor(private stateService: StateService) { }
  ngOnInit() {
    this.isRelated$ = this.stateService.areSelectedConnected().
    pipe(tap(_ => console.log(_)), map(_ => this.buttonText = _ ?   'disconnect' : 'connect'), distinctUntilChanged());
  }
  ngAfterViewInit() {
    this.buttonStream = fromEvent(this.button.nativeElement, 'click')
    // @ts-ignore
    .subscribe(_ => this.stateService.changeRelationshipSelected(_.target.innerHTML));
  }
  ngOnDestroy(): void {
    this.buttonStream.unsubscribe();
  }
}
