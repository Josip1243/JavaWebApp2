import { Component, OnInit } from '@angular/core';
import { UserService } from '../user/user.service';
import { LoginService } from '../login/login.service';
import { User } from '../user/user.model';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { LanguageEnum } from '../constants/language.enum';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  // @ts-ignore
  isNavbarCollapsed: boolean;
  // @ts-ignore
  currentLanguage: string;

  constructor(
    private loginService: LoginService,
    private router: Router,
    private translateService: TranslateService,
    public userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe((currentUser: User) => {
      this.userService.currentUser = currentUser;
    });
    this.setCurrentLanguageDropdownValue();
  }

  setCurrentLanguageDropdownValue() {
    if (this.translateService.currentLang === LanguageEnum.HR) {
      this.translateService.get('language.croatian').subscribe(language => this.currentLanguage = language);
    } else if (this.translateService.currentLang === LanguageEnum.EN) {
      this.translateService.get('language.english').subscribe(language => this.currentLanguage = language);
    } else {
      throw Error('Unknown current language!');
    }
  }

  toggleNavbar() {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  logout() {
    this.loginService.logout();
    // @ts-ignore
    this.userService.currentUser = null;
    this.router.navigate(['/login']);
  }

  isUserLoggedIn(): boolean {
    return !!this.userService.currentUser;
  }

  onLanguageChange(newLanguage: string) {
    this.translateService.use(newLanguage).subscribe(
      languageSwitched => this.setCurrentLanguageDropdownValue()
    );
  }
}
