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

  onCloseModal() {
    console.log("modal has been closed");
    $('#exampleModal').modal('hide');  // Optional: if you want to close modal programmatically
  }

  onSaveChanges() {
    console.log("data saved");
    $('#exampleModal').modal('hide');  // Optional: close modal after saving changes
  }
}
