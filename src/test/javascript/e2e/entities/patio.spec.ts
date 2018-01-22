import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Patio e2e test', () => {

    let navBarPage: NavBarPage;
    let patioDialogPage: PatioDialogPage;
    let patioComponentsPage: PatioComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Patios', () => {
        navBarPage.goToEntity('patio');
        patioComponentsPage = new PatioComponentsPage();
        expect(patioComponentsPage.getTitle())
            .toMatch(/controleEstacionamentoApp.patio.home.title/);

    });

    it('should load create Patio dialog', () => {
        patioComponentsPage.clickOnCreateButton();
        patioDialogPage = new PatioDialogPage();
        expect(patioDialogPage.getModalTitle())
            .toMatch(/controleEstacionamentoApp.patio.home.createOrEditLabel/);
        patioDialogPage.close();
    });

    it('should create and save Patios', () => {
        patioComponentsPage.clickOnCreateButton();
        patioDialogPage.setDescricaoInput('descricao');
        expect(patioDialogPage.getDescricaoInput()).toMatch('descricao');
        patioDialogPage.setQtdVagasInput('5');
        expect(patioDialogPage.getQtdVagasInput()).toMatch('5');
        patioDialogPage.setTaxaHoraInput('5');
        expect(patioDialogPage.getTaxaHoraInput()).toMatch('5');
        patioDialogPage.save();
        expect(patioDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PatioComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-patio div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class PatioDialogPage {
    modalTitle = element(by.css('h4#myPatioLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    descricaoInput = element(by.css('input#field_descricao'));
    qtdVagasInput = element(by.css('input#field_qtdVagas'));
    taxaHoraInput = element(by.css('input#field_taxaHora'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setDescricaoInput = function(descricao) {
        this.descricaoInput.sendKeys(descricao);
    }

    getDescricaoInput = function() {
        return this.descricaoInput.getAttribute('value');
    }

    setQtdVagasInput = function(qtdVagas) {
        this.qtdVagasInput.sendKeys(qtdVagas);
    }

    getQtdVagasInput = function() {
        return this.qtdVagasInput.getAttribute('value');
    }

    setTaxaHoraInput = function(taxaHora) {
        this.taxaHoraInput.sendKeys(taxaHora);
    }

    getTaxaHoraInput = function() {
        return this.taxaHoraInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
