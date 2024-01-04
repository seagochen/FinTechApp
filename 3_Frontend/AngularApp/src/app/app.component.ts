import { Component, HostListener, NgZone, OnInit } from '@angular/core';
import { CommonModule } from "@angular/common";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: true,
  styleUrls: ['./app.component.css'],
  imports: [
    CommonModule, // 确保导入 CommonModule,
  ],
})

export class AppComponent implements OnInit {
  public time: string; // 用于显示时间的属性
  public displayClock: boolean; // 控制时钟是否显示
  // private cdr: any;

  constructor(private ngZone: NgZone) {
    this.displayClock = true; // 默认显示时钟
    this.time = ''; // 初始化时间字符串
  }

  ngOnInit() {
    this.updateClockDisplay(window.innerWidth);
    this.showTime();
  }

  // 当窗口大小发生变化时调用
  @HostListener('window:resize', ['$event'])
  onResize(event: Event) {
    this.updateClockDisplay((event.target as Window).innerWidth);
  }

  // 更新时钟显示的方法
  updateClockDisplay(width: number) {
    this.displayClock = width >= 768;
  }

  // 时钟逻辑
  showTime() {
    this.ngZone.runOutsideAngular(() => {
      setInterval(() => {
        const date = new Date();
        let hours: string | number = date.getHours();
        let minutes: string | number = date.getMinutes();
        let seconds: string | number = date.getSeconds();

        hours = hours < 10 ? '0' + hours : hours;
        minutes = minutes < 10 ? '0' + minutes : minutes;
        seconds = seconds < 10 ? '0' + seconds : seconds;

        // 输出时间到控制台以验证其是否正在更新
        // console.log(this.time);

        // 使用NgZone确保视图可以更新
        this.ngZone.run(() => {
          this.time = `${hours}:${minutes}:${seconds}`;
          // this.cdr.detectChanges(); // 强制执行变更检测
        });
      }, 1000);
    });
  }
}
