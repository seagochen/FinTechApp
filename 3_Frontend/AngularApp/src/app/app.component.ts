import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})


export class AppComponent {
  public title_h1 = 'Fuck you!';
  public title_h2 = "Fuck you again!";

  // public image_url = "assets/new_logo.svgz"
  public image_url = "assets/profile.png"

  // onClick event
  public onClick() {
    // console show hello world
    console.log("Hello wolrd!");
  }
}
