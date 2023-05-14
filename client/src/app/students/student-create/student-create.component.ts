import { Component, OnInit } from '@angular/core';
import { Student } from '../student.model';

@Component({
  selector: 'app-student-create',
  templateUrl: './student-create.component.html'
})
export class StudentCreateComponent implements OnInit {

  // @ts-ignore
  student: Student;

  constructor() { }

  ngOnInit(): void {
    // @ts-ignore
    this.student = new Student();
  }

}
