import { Component, OnInit, Input } from '@angular/core';
import { Student } from '../student.model';
import { ActivatedRoute } from '@angular/router';
import { StudentService } from '../student.service';
import { CourseService } from '../../course/course.service';
import { Course } from '../../course/course.model';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-student-detail',
  templateUrl: './student-detail.component.html'
})
export class StudentDetailComponent implements OnInit {
  // @ts-ignore
  @Input() student: Student;
  // @ts-ignore
  courses: Course[];

  constructor(
    private route: ActivatedRoute,
    private studentService: StudentService,
    private courseService: CourseService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.pipe(
      switchMap(params => {
        const jmbag = params.get('jmbag');
          console.log(jmbag)
          // @ts-ignore
        return this.studentService.getStudent(jmbag);
      }
      )
    ).subscribe((student: Student) => {
      this.student = student;
      this.courseService.getCoursesByJmbag(student.jmbag).subscribe(
        courses => { this.courses = courses
          console.log(courses)}
      );


    });
  }

}
