import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  form = this.fb.group({
      username: ['', Validators.required]
  });
  constructor(private router: Router,
              private fb: FormBuilder) { }

  ngOnInit() {
  }


  submit() {
    this.router.navigate(['/rooms']);
  }
}
